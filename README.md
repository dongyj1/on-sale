# webstore with high concurrency support
A high concurrency web site achieves good performance under high load with distributed framework and caching system.

Feature highlights:

1. Integrated a redis caching system for caching frequent infomation for enhancing the performance of data fetching and saving.

2. Users who login to the system are assign a token with expire time for access control, which is a popular design on distributed session.

3. Applied jsr303 validation for a reusable and powerful parameter check.

4. Used ControllerAdvice for global exception handler, avoiding redundent exception catcher and handler.

To be added:

1. Druid, a powerful connection pooling manager and monitor developed by Alibaba, can be added for instant and periodic connection check.

2. A message queue, probably RabbitMQ, for user order queueing, to reduce the database pressure.




