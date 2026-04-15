-- Alter beer_order_shipment table to allow NULLs for carrier and tracking_number
ALTER TABLE beer_order_shipment ALTER COLUMN carrier DROP NOT NULL;
ALTER TABLE beer_order_shipment ALTER COLUMN tracking_number DROP NOT NULL;
