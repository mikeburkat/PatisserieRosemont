CREATE TABLE customer ( 
    cid         INTEGER           PRIMARY KEY ASC AUTOINCREMENT
                                  NOT NULL
                                  UNIQUE,
    name        VARCHAR( 0, 50 )  NOT NULL,
    city        VARCHAR( 0, 30 )  NOT NULL,
    address     TEXT,
    postal_code TEXT( 0, 7 ),
    phone_num   TEXT 
);

CREATE TABLE placed_order ( 
    cid  INTEGER NOT NULL
                 REFERENCES customer ( cid ),
    oid  INTEGER NOT NULL
                 REFERENCES orders ( oid ),
    date DATE    NOT NULL 
);

CREATE TABLE orders ( 
    oid         INTEGER PRIMARY KEY ASC AUTOINCREMENT
                        NOT NULL,
    total       REAL    NOT NULL
                        DEFAULT ( 0.0 ),
    paid_status BOOLEAN NOT NULL
                        DEFAULT ( 0 ) 
);

CREATE TABLE contained ( 
    oid       INTEGER NOT NULL REFERENCES orders ( oid ),
    pid       INTEGER NOT NULL REFERENCES product ( pid ),
    quantity  REAL    NOT NULL DEFAULT ( 0.0 ),
    qub_total REAL    NOT NULL DEFAULT ( 0.0 ) 
);

CREATE TABLE product ( 
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
    date_effective DATETIME          NOT NULL,
    date_end                         NOT NULL,
    date_replaced  DATETIME          NOT NULL,
    original_id    INTEGER 
);


