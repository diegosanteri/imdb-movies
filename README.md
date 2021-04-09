# imdb-api

This project exposes the top 1000 movies from IMDB and indexes the data
on in-memory database. This in-memory database contains a simple full-text search-based query. 
In order to achieve full-text search I created an inverted-index responsible for storing data ID related to the 
searched word. Besides, we have a simple token steemer which is being used to enhance our search better. 
In this case, it is possible to search for part of the string. For example, there would be the same results for Spielber or Spielberg. 


<!-- GETTING STARTED -->
## Getting Started

In order to run it we can just run the command

`./mvnw spring-boot:run`

While the project is running we are going to have the following messages:

```
Starting fetch movie IMDB offset 1
Starting fetch movie IMDB offset 201
Starting fetch movie IMDB offset 301
Starting fetch movie IMDB offset 401
Starting fetch movie IMDB offset 501
Starting fetch movie IMDB offset 601
Starting fetch movie IMDB offset 701
Starting fetch movie IMDB offset 801
Starting fetch movie IMDB offset 901
Starting fetch movie IMDB offset 1001

1000 were imported
```

After that, all data from IMDB top 1000 movies would be properly imported to our application.
In order to access the search endpoint, the following URL would need be invoked:
`http://localhost:8080/movies?search=spielberg`