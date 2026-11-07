package com.shoplab.order.billing;

import com.shoplab.order.rules.BusinessDay;
import java.sql.Timestamp;
import javax.sql.DataSource;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Nightly settlement: every order of the day gets its business day and tax. All or nothing (rule 9). */
@Configuration
public class SettlementJobConfig {

    record Row(long id, Timestamp placedAt, long totalPaise, String state) {
    }

    @Bean
    JdbcCursorItemReader<Row> orders(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Row>()
            .name("orders")
            .dataSource(dataSource)
            .sql("SELECT id, placed_at, total_paise, state FROM orders WHERE settled = FALSE")
            .rowMapper((rs, n) -> new Row(rs.getLong(1), rs.getTimestamp(2), rs.getLong(3), rs.getString(4)))
            .build();
    }

    @Bean
    ItemProcessor<Row, Settlement> settle() {
        return row -> new Settlement(row.id(), BusinessDay.of(row.placedAt().toInstant()), row.totalPaise(),
            TaxRates.taxOn(row.totalPaise(), row.state()));
    }

    @Bean
    JdbcBatchItemWriter<Settlement> settlements(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Settlement>()
            .dataSource(dataSource)
            .sql("INSERT INTO settlements (order_id, business_day, amount_paise, tax_paise) VALUES (?, ?, ?, ?)")
            .itemPreparedStatementSetter((s, ps) -> {
                ps.setLong(1, s.orderId());
                ps.setObject(2, s.businessDay());
                ps.setLong(3, s.amountPaise());
                ps.setLong(4, s.taxPaise());
            })
            .build();
    }

    @Bean
    Step settleStep(JobRepository jobs, JdbcCursorItemReader<Row> orders, ItemProcessor<Row, Settlement> settle,
                    JdbcBatchItemWriter<Settlement> settlements) {
        return new StepBuilder("settle", jobs)
            .<Row, Settlement>chunk(100)
            .reader(orders)
            .processor(settle)
            .writer(settlements)
            .build();
    }

    @Bean
    Job settlementJob(JobRepository jobs, Step settleStep) {
        return new JobBuilder("settlement", jobs).start(settleStep).build();
    }
}
