raw_data = LOAD '/shopper_project/data.csv' USING PigStorage(',');
projected_data = FOREACH raw_data GENERATE (chararray)$0 AS user_id, (int)$1 AS age, (chararray)$2 AS gender;
clean_data = FILTER projected_data BY user_id != 'user_id';
young_adults = FILTER clean_data BY age >= 18 AND age <= 25;
STORE young_adults INTO '/pig_out_t2' USING PigStorage(',');
