# Munros API

## Building + running

To build and run use:

```
./mvnw spring-boot:run
```

To run tests:

```
mvn verify
```

## Using the API

### Populating with data
The application doesn't persist data to a DB. So it will need to be seeded on first run.

To seed the DB a POST request needs to be made to the `/munros/upload` route with form-data field key `file` - the value should be the CSV file. This is most easily done in POSTman or Insomnia.

### Querying the data

Note: before querying the POST will need to be made to `/munros/upload` as mentioned above

 * GET `/munros` - For allowing users to GET data about the munros. Optional query string params can be passed to this endpoint to customise and filter the results. The following queries are available:

 * `category=MUN|TOP`    - pass either MUN or TOP as values to category to filter by that category
 * `sortBy=height|name`  - pass either height or name to sortBy to sort by height in meters or name
 * `desc=true|false`     - pass either true|false to desc to sortBy descending or ascending (descending is default)
 * `minHeight=_number_` - pass a decimal number to minHeight to get munros OVER that height
 * `maxHeight=_number_` - pass a decimal number to maxHeight to get munros UNDER that height


 ## Code Structure

 The code is structured _as much as possible_ like a typical Spring-Boot service application. 
 The heart of the application is the `MunroController` as this serves as the main entry point for accessing the API via HTTP. The `MunroController` is a singleton bean (`@RestController`). Main duties are serving the endpoints and keeping track of the application state in the form of the List of Munros. (not normal for the controllers to keep track of data - but this isn't backed by a database or even JPA) Once the application starts this list will be null. Trying to query for the data via the `/munros` endpoint will lead to a 404 error and suitable description of what's happened.

 The list of munros in `MunroController` could be placed in other parts of the code just as easily. For example the `MunroRepositoryImpl` or a `MunroService` (which doesn't exist). Decision was made to keep it in `MunroController` for now. If this was a more typical SpringBoot application that persisted data to a Database of some kind, JPA would've been used and the placement of the data wouldn't be an issue.

 **The `DataLoader`** methods in here serve the function of loading data from a file on the filesystem of the local machine but doubles up as loading data in from the controllers. Looking at `parseCSVFile(BufferedReader reader)` - if a `BufferedReader` object isn't provided it will attempt to load a CSV from the filesystem. Since it's possible to pass a reader to `parseCSVFIle` it can be used by the controller to take data when it's POSTed as a MultipartFile.

One of the most important methods in that class is `createMunrosFromData(List<String> listOfLines, LinkedList<Integer> columnIndexes)` - this accepts a list of Strings as well as a list of column indexes. It then pulls the data out of the Strings at those columns and provides a List of Munro Objects using the Munro model.
