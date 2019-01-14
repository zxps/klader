## Kladr database tool

An easy-to-use tool to download the latest kladr 
database dbf files from the official http://www.gnivc.ru website.

#### Features
* Download latest Kladr database from official website.
* Very simple SQL like query tool
* No database to install needed - klader tool works only with downloaded dbf files

#### Requirements
* Maven
* Java 1.8+

#### Installation
* Run `mvn package` from the source directory
* Download the latest kladr archive with `bin/tool -renew`

#### How to use
* How to select first 100000 cities
``` bin/tool -q "Select * FROM city LIMIT 100000" ```

* Select first 100000 streets
``` bin/tool -q "Select * FROM street LIMIT 1000000" ```

* How to select first 1000 city names sorted by name [from А to Я]
``` bin/tool -q "Select name FROM city SORT name asc LIMIT 1000"```

* How to find city code for "Санкт-Петербург" 
``` bin/tool -q "Select code FROM city WHERE name MATCH ^Санкт-Петербург.*"```

* How to find cities whose names are like Санкт-Петербург or Москва
``` bin/tool -q "select * from city where name match ^Санкт-Петербург$ or name match ^Москва$```

* How to get the number of items found in the previous query 
``` bin/tool -q "select COUNT from city where name match ^Санкт-Петербург$ or name match ^Москва$```

