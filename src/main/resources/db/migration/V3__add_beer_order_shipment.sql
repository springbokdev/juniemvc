-- Create table beer_order_shipment with standard auditing columns (from BaseEntity)
CREATE TABLE beer_order_shipment (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    version INTEGER,
    created_date TIMESTAMP,
    update_date TIMESTAMP,
    shipment_date TIMESTAMP NOT NULL,
    carrier VARCHAR(255) NOT NULL,
    tracking_number VARCHAR(255) NOT NULL,
    beer_order_id INTEGER
);

-- Add foreign key constraint to beer_order(id) in a separate ALTER TABLE statement
ALTER TABLE beer_order_shipment 
ADD CONSTRAINT fk_beer_order_shipment_beer_order 
FOREIGN KEY (beer_order_id) REFERENCES beer_order(id);
