Generate using Streams N POJO messages
(N is given as the startup argument, N > 1000
pojo: {name: “text”, count: xxx, created_at: date_time} random)
And send them to one ActiveMq queue <IN-queue>,
Take out the connection/queue name in properties,
Stop generation - by time (parameter in seconds from the property) via "poison pill"
Read messages from the IN-queue (simultaneously with generation, 
  who is interested in parallel work or after generation, for simplicity), run through validators
(name length >=7, there is at least one letter 'a', count >= 10)
It is valid to write in one csv-file with two fields name & count
Invalid - into another csv file with an additional error field in the form of json {“errors”:[]}.
