CREATE TABLE users (
  id INT PRIMARY KEY IDENTITY (1, 1),
  username VARCHAR(100),
  password VARCHAR(300),
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  role VARCHAR(200),
  status VARCHAR(20)
)