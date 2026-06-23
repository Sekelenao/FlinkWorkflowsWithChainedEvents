package io.github.sekelenao.delay;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.legacy.SourceFunction;

public final class Main {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new DummySource())
           .name("dummy-source")
           .print()
           .name("dummy-print");

        env.execute("DelayWorkflow");
    }

    private static class DummySource implements SourceFunction<String> {
        private volatile boolean isRunning = true;

        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            long count = 0;
            while (isRunning) {
                ctx.collect("Dummy event " + count++);
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }
}

