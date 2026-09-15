raw_data = LOAD '/shopper_project/data.csv' USING PigStorage(',');
-- Load user_id ($0), country ($3), and cast income ($5) as a numeric double
projected_data = FOREACH raw_data GENERATE (chararray)$0 AS user_id, (chararray)$3 AS country, (double)$5 AS income;
clean_data = FILTER projected_data BY user_id != 'user_id' AND income IS NOT NULL;

-- Group records by country to aggregate the values
grouped_data = GROUP clean_data BY country;

-- Calculate the absolute highest income found in each country
country_income = FOREACH grouped_data GENERATE group AS country, MAX(clean_data.income) AS highest_income;

-- Sort by the highest income in descending order and limit to top 10
sorted_income = ORDER country_income BY highest_income DESC;
top_10_highest = LIMIT sorted_income 10;

STORE top_10_highest INTO '/pig_out_t4' USING PigStorage(',');
