raw_data = LOAD '/shopper_project/data.csv' USING PigStorage(',');
projected_data = FOREACH raw_data GENERATE (chararray)$0 AS user_id, (chararray)$3 AS country;
clean_data = FILTER projected_data BY user_id != 'user_id';
grouped_country = GROUP clean_data BY country;
counted_country = FOREACH grouped_country GENERATE group AS country, COUNT(clean_data) AS total_shoppers;
sorted_country = ORDER counted_country BY total_shoppers DESC;
STORE sorted_country INTO '/pig_out_t1' USING PigStorage(',');
