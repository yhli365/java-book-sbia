insert into Reader (username, password, fullname) values ('craig', 'password', 'Craig Walls');
insert into Reader (username, password, fullname) values ('walt', 'marceline', 'Walt Disney');

--Table: Book
insert into Book (reader_username, isbn, title, author, description) 
  values ('craig', '978-7-115-43314-5', 'Spring Boot In Action', 'Craig Walls', 'Spring Boot');
insert into Book (reader_username, isbn, title, author, description) 
  values ('craig', '978-7-121-31301-1', 'Spring Cloud微服务实战', '翟永超', 'Spring Cloud');
insert into Book (reader_username, isbn, title, author, description) 
  values ('walt', '978-7-115-43314-5', 'Spring Boot In Action', 'Craig Walls', 'Spring');
  