raw_data = LOAD '/shopper_project/data.csv' USING PigStorage(',');
projected_data = FOREACH raw_data GENERATE (chararray)$0 AS user_id, (chararray)$4 AS urban_rural;
clean_data = FILTER projected_data BY user_id != 'user_id';
grouped_regions = GROUP clean_data BY urban_rural;
counted_regions = FOREACH grouped_regions GENERATE group AS region_type, COUNT(clean_data) AS total_count;
STORE counted_regions INTO '/pig_out_t3' USING PigStorage(',');
