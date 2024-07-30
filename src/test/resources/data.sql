INSERT INTO tool_type_info (id, type, daily_charge_amount, weekday_charge, weekend_charge, holiday_charge) VALUES (1, 'LADDER', 1.99, true, true, false);
INSERT INTO tool_type_info (id, type, daily_charge_amount, weekday_charge, weekend_charge, holiday_charge) VALUES (2, 'CHAINSAW', 1.49, true, false, true);
INSERT INTO tool_type_info (id, type, daily_charge_amount, weekday_charge, weekend_charge, holiday_charge) VALUES (3, 'JACKHAMMER', 2.99, true, false, false);


INSERT INTO tool (id, tool_code, type_info_id, brand) VALUES (1, 'CHNS', (SELECT id FROM tool_type_info WHERE type = 'CHAINSAW'), 'Stihl');
INSERT INTO tool (id, tool_code, type_info_id, brand) VALUES (2, 'LADW', (SELECT id FROM tool_type_info WHERE type = 'LADDER'), 'Werner');
INSERT INTO tool (id, tool_code, type_info_id, brand) VALUES (3, 'JAKD', (SELECT id FROM tool_type_info WHERE type = 'JACKHAMMER'), 'DeWalt');
INSERT INTO tool (id, tool_code, type_info_id, brand) VALUES (4, 'JAKR', (SELECT id FROM tool_type_info WHERE type = 'JACKHAMMER'), 'Ridgid');