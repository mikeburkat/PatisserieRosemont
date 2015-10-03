
-- Table: customers
CREATE TABLE customers ( 
    cid          INTEGER           PRIMARY KEY ASC AUTOINCREMENT
                                   NOT NULL
                                   UNIQUE,
    name         VARCHAR( 0, 50 )  NOT NULL,
    city         VARCHAR( 0, 30 )  NOT NULL,
    address      TEXT,
    postal_code  TEXT( 0, 7 ),
    phone_num    TEXT,
    price_used   VARCHAR( 0, 25 )  NOT NULL
                                   DEFAULT ( 'montreal_price' ),
    address_city TEXT( 0, 30 ) 
);


-- Table: products
CREATE TABLE products ( 
    pid            INTEGER           PRIMARY KEY ASC AUTOINCREMENT
                                     NOT NULL,
    name           VARCHAR( 0, 50 )  NOT NULL,
    category       VARCHAR( 0, 20 )  NOT NULL,
    montreal_price REAL              NOT NULL,
    ottawa_price   REAL              NOT NULL,
    kosciol_price  REAL              NOT NULL,
    cecil_price    REAL              NOT NULL,
    rosemont_price REAL              NOT NULL,
    updated        BOOLEAN           NOT NULL
                                     DEFAULT ( 0 ),
    date_created   DATETIME          NOT NULL,
    date_replaced  DATETIME,
    original_id    INTEGER 
);


-- Table: orders
CREATE TABLE orders ( 
    oid         INTEGER PRIMARY KEY ASC AUTOINCREMENT
                        NOT NULL,
    total       REAL    NOT NULL
                        DEFAULT ( 0.0 ),
    paid_status BOOLEAN NOT NULL
                        DEFAULT ( 0 ) 
);


-- Table: placed_order
CREATE TABLE placed_order ( 
    cid        INTEGER NOT NULL
                       REFERENCES customers ( cid ) ON DELETE NO ACTION,
    oid        INTEGER NOT NULL
                       UNIQUE ON CONFLICT IGNORE
                       REFERENCES orders ( oid ) ON DELETE NO ACTION,
    order_date DATE    NOT NULL,
    CONSTRAINT 'prim_key' PRIMARY KEY ( cid ASC, order_date ASC )  ON CONFLICT REPLACE,
    CONSTRAINT 'uniq_prim_key' UNIQUE ( cid ASC, order_date ASC )  ON CONFLICT REPLACE 
);


-- Table: contained
CREATE TABLE contained ( 
    oid       INTEGER NOT NULL
                      REFERENCES orders ( oid ) ON DELETE NO ACTION,
    pid       INTEGER NOT NULL
                      REFERENCES products ( pid ) ON DELETE NO ACTION,
    quantity  REAL    NOT NULL
                      DEFAULT ( 0.0 ),
    sub_total REAL    NOT NULL
                      DEFAULT ( 0.0 ),
    price     REAL    NOT NULL
                      DEFAULT ( 0.0 ),
    CONSTRAINT 'prim_key_contained' PRIMARY KEY ( oid ASC, pid ASC )  ON CONFLICT REPLACE,
    CONSTRAINT 'uniq_product_per_order' UNIQUE ( oid ASC, pid ASC )  ON CONFLICT REPLACE 
);


-- Trigger: sub_total_price_quantity_insert
CREATE TRIGGER sub_total_price_quantity_insert
       AFTER INSERT ON contained
       FOR EACH ROW
BEGIN
    UPDATE contained
       SET sub_total = quantity * price
     WHERE oid = NEW.oid 
           AND
           pid = NEW.pid;
END;
;


-- Trigger: sub_total_price_quantity_update
CREATE TRIGGER sub_total_price_quantity_update
       AFTER UPDATE ON contained
       FOR EACH ROW
BEGIN
    UPDATE contained
       SET sub_total = quantity * price
     WHERE oid = NEW.oid 
           AND
           pid = NEW.pid;
END;
;

