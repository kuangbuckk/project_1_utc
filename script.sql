CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    full_name           VARCHAR(100)          DEFAULT '',
    phone_number        varchar(15)           DEFAULT '',
    email               VARCHAR(120) NOT NULL UNIQUE,
    address             VARCHAR(200)          DEFAULT '',
    password            VARCHAR(200) NOT NULL DEFAULT '',
    created_at          TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    is_active           smallint              DEFAULT 1,
    date_of_birth       DATE,
    facebook_account_id INT                   DEFAULT 0,
    google_account_id   INT                   DEFAULT 0,
    role_id             INT          NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

ALTER TABLE users
ADD COLUMN organization_id INT REFERENCES organizations (id) DEFAULT NULL ON DELETE SET NULL;
--quản lý ban tổ chức và admin sẽ bàn giao quyền cho user bằng cách thay đổi organization_id cho user trên dashboard

CREATE TABLE user_avatars
(
    id        SERIAL PRIMARY KEY,
    image_url varchar(300) NOT NULL,
    user_id   INT REFERENCES users (id) ON DELETE CASCADE UNIQUE
);


CREATE TABLE social_accounts
(
    id          SERIAL PRIMARY KEY,
    provider    VARCHAR(20)  NOT NULL,
    provider_id VARCHAR(50)  NOT NULL,
    email       VARCHAR(150) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    user_id     int,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE organizations
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL
);

CREATE TABLE events
(
    id              SERIAL PRIMARY KEY,
    name            varchar(100) NOT NULL,
    description     VARCHAR(400) DEFAULT '',
    location        varchar(255) DEFAULT '' NOT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    organization_id INT REFERENCES organizations ON DELETE CASCADE NOT NULL,
    category_id     INT REFERENCES categories (id) ON DELETE SET NULL
);
ALTER TABLE events
ADD COLUMN start_date TIMESTAMP,
ADD COLUMN end_date TIMESTAMP;

ALTER TABLE events
ADD COLUMN status NOT NULL DEFAULT 'pending';
--pending, active, cancelling, cancelled, completed: organizer chỉ có thể tạo event ở trạng thái pending,
--admin có thể duyệt event từ pending sang active, hoặc từ active sang cancelled, hoặc từ active sang completed


CREATE TABLE event_images
(
    id        SERIAL PRIMARY KEY,
    image_url varchar(300) UNIQUE NOT NULL,
    event_id  INT REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE ticket_categories(
    id          SERIAL PRIMARY KEY,
    category_name     varchar(255)  NOT NULL,
    price             int  NOT NULL,
    remaining_count    int  NOT NULL,  -- New field to define ticket limit per category
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    event_id    INT REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE tickets
(
    id          SERIAL PRIMARY KEY,
    status      varchar(20) DEFAULT 'active', -- active pending cancelled
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
ticket_category_id    INT REFERENCES ticket_categories (id) ON DELETE CASCADE,
    user_id INT REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE ticket_orders
(
    id               SERIAL PRIMARY KEY,
    user_id          int          DEFAULT NULL REFERENCES users(id),
    order_date       date         DEFAULT current_timestamp,
    total_money      float        DEFAULT NULL CHECK (total_money >= 0),
    payment_method   varchar(100) DEFAULT NULL,
    payment_status   varchar(100) DEFAULT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE ticket_orders
    ADD COLUMN stripe_token_id varchar(255) NOT NULL UNIQUE default '';

ALTER TABLE ticket_orders
    ADD COLUMN email varchar(255) NOT NULL default '';

CREATE TABLE ticket_order_details
(
    id                 SERIAL PRIMARY KEY,
    ticket_order_id    int         DEFAULT NULL REFERENCES ticket_orders(id),
    ticket_category_id int         DEFAULT NULL REFERENCES ticket_categories(id),
    number_of_tickets int         DEFAULT NULL CHECK (number_of_tickets > 0),
    total_money        float       DEFAULT NULL CHECK (total_money >= 0),
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Index on email in the users table for fast lookups
CREATE INDEX idx_users_email ON users (email);

-- Index on organization_id in the events table for filtering events by organization
CREATE INDEX idx_events_organization_id ON events (organization_id);

-- Index on user_id and ticket_categories_id in the tickets table for fast lookups and filtering
CREATE INDEX idx_tickets_user_id ON tickets (user_id);
CREATE INDEX idx_tickets_ticket_categories_id ON tickets (ticket_category_id);
CREATE INDEX idx_tickets_status ON tickets (status);

-- Index on user_id in the ticket_orders table for filtering orders by user
CREATE INDEX idx_ticket_orders_user_id ON ticket_orders (user_id);

-- Index on payment_status in the ticket_orders table for fast lookups of payment status
CREATE INDEX idx_ticket_orders_payment_status ON ticket_orders (payment_status);

-- Index on ticket_order_id and ticket_category_id in the ticket_order_details table for filtering details by order and category
CREATE INDEX idx_ticket_order_details_ticket_order_id ON ticket_order_details (ticket_order_id);
CREATE INDEX idx_ticket_order_details_ticket_category_id ON ticket_order_details (ticket_category_id);