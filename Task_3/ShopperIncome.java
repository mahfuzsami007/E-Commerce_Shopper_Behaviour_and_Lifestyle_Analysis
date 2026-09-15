import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ShopperIncome {
    public static class IncomeMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text incomeText = new Text();
        private Map<String, Integer> headerMap = new HashMap<>();
        private boolean isHeaderParsed = false;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (!isHeaderParsed) {
                for (int i = 0; i < tokens.length; i++) {
                    headerMap.put(tokens[i].replace("\"", "").trim().toLowerCase(), i);
                }
                isHeaderParsed = true;
                return;
            }

            Integer incomeIndex = headerMap.get("income_level");
            if (incomeIndex != null && incomeIndex < tokens.length) {
                String income = tokens[incomeIndex].replace("\"", "").trim();
                if (!income.isEmpty()) {
                    incomeText.set(income);
                    context.write(incomeText, one);
                }
            }
        }
    }

    public static class IncomeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) { sum += val.get(); }
            context.write(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Shopper Count by Income Level");
        job.setJarByClass(ShopperIncome.class);
        job.setMapperClass(IncomeMapper.class);
        job.setReducerClass(IncomeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
