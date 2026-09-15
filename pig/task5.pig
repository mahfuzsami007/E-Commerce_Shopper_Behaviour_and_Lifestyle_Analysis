raw_data = LOAD '/shopper_project/data.csv' USING PigStorage(',');
projected_data = FOREACH raw_data GENERATE (chararray)$0 AS user_id, (chararray)$2 AS gender, (chararray)$5 AS income_level;
clean_data = FILTER projected_data BY user_id != 'user_id';
grouped_segments = GROUP clean_data BY (gender, income_level);
counted_segments = FOREACH grouped_segments GENERATE group.gender AS gender, group.income_level AS income_level, COUNT(clean_data) AS segment_count;
STORE counted_segments INTO '/pig_out_t5' USING PigStorage(',');
