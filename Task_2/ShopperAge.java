import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ShopperAge {
    public static class AgeMapper extends Mapper<Object, Text, Text, DoubleWritable> {
        private final static Text dummyKey = new Text("Average Age");
        private DoubleWritable ageVal = new DoubleWritable();
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

            Integer ageIndex = headerMap.get("age");
            if (ageIndex != null && ageIndex < tokens.length) {
                try {
                    double age = Double.parseDouble(tokens[ageIndex].replace("\"", "").trim());
                    ageVal.set(age);
                    context.write(dummyKey, ageVal);
                } catch (NumberFormatException e) {}
            }
        }
    }

    public static class AgeReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            double sum = 0;
            long count = 0;
            for (DoubleWritable val : values) {
                sum += val.get();
                count++;
            }
            context.write(key, new DoubleWritable(sum / count));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Average Shopper Age");
        job.setJarByClass(ShopperAge.class);
        job.setMapperClass(AgeMapper.class);
        job.setReducerClass(AgeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
