# A simple server for a two-sum problem

Can be run with:
`sbt run`

Has only one route which can be tested with:
`curl -d "[[11, -4, 3, 4, 3, 2], [2, 5, 5, 3, 0, 1]]" -X POST http://localhost:8080/upload/7`

I wrote a custom parser to be able to give more detailed error messages.

I made it simple with no interfaces, which in my opinion suites application size.

I don't check whether the request is text/csv.

I didn't know if I should have list pairs twice, so I return pairs distinct.

## Further improvements

- Move port to a configuration file
- Add logging
- Add scalafmt config
