# Shop Lab

A microservice shop for an Indian retailer, on Spring Boot 4.

| Service | Does | Talks to |
|---|---|---|
| order-service (8081) | orders, carts, stock, prices, nightly settlement (Spring Batch) | payment-service over Feign; Redis for cached prices; JDBC |
| payment-service (8082) | charges cards, fraud limits | Redis for idempotency keys; the card gateway |

All money is in paise (1 rupee = 100 paise) and held as `long`.

## Business rules

1. **One discount per order.** A coupon and a loyalty discount never stack: the customer gets whichever is larger.
2. **The business day is India time.** An order belongs to the calendar day in Asia/Kolkata; reports, settlement
   and daily limits all use this day.
3. **Bills split exactly.** When a bill is split between people, the parts must add up to the total; leftover
   paise go to the first people, one each.
4. **The fraud limit is inclusive.** A payment exactly at the customer's limit is allowed; only amounts above it
   are blocked.
5. **Cancelling releases what was reserved.** Stock reserved for an order may be less than the quantity ordered
   (back-orders); cancelling must give back only what was actually reserved.
6. **Prices are stored in rupees.** Converting to a foreign currency divides by the rate (rupees per unit of that
   currency); converting back multiplies.
7. **A payment is charged once.** A charge is identified by its idempotency key, kept in Redis for 24 hours; retries
   after a timeout must reuse the same key so the gateway does not charge twice.
8. **Pages are numbered from 1.** Page 1 is the first page of results.
9. **Settlement is all or nothing.** If any order of the day cannot be settled, the nightly run fails and is retried;
   it never quietly skips orders.
10. **Cached prices are per currency.** A price cached for one currency must never be served for another.

## About this repository

This is a test project for [Codist](https://github.com/AbiXnash/codist), built commit by commit by
`testbed/make_shop_lab.py` in [codist-eval](https://github.com/AbiXnash/codist-eval). Some commits contain
planted bugs; the answer key and the scores are kept in codist-eval, not here.
