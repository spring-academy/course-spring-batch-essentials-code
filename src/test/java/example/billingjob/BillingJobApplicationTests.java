package example.billingjob;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class BillingJobApplicationTests {

	@Autowired
	private Job job;

	@Autowired
	private JobOperator jobOperator;

	@Test
	void testJobExecution(CapturedOutput output) throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", "/some/input/file")
				.toJobParameters();

		// when
		JobExecution jobExecution = this.jobOperator.run(this.job, jobParameters);

		// then
		Assertions.assertTrue(output.getOut().contains("processing billing information from file /some/input/file"));
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}

}