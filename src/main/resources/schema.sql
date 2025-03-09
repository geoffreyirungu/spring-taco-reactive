
create table ingredient(
    id identity,
    slug varchar(4) not null,
    name varchar (25) not null,
    type varchar (10) not null
);

create table taco(
    id identity,
    created_at timestamp not null,
    name varchar(50) not null,
    ingredient_ids integer array
);

create table Taco_Order (
  id identity,
  delivery_name varchar(50) not null,
  delivery_street varchar(50) not null,
  delivery_city varchar(50) not null,
  delivery_state varchar(2) not null,
  delivery_zip varchar(10) not null,
  cc_number varchar(16) not null,
  cc_expiration varchar(5) not null,
  cc_cvv varchar(3) not null,
  taco_ids integer array
);