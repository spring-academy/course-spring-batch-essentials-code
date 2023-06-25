package example.billingjob;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.test.JobOperatorTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(properties = "spring.batch.job.enabled=false")
class BillingJobApplicationTests {

	@Autowired
	private JobOperatorTestUtils jobOperatorTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@BeforeEach
	public void setUp() {
		this.jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	void testJobExecution() throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", "src/main/resources/billing-2026-01.csv")
				.toJobParameters();

		// when
		JobExecution jobExecution = this.jobOperatorTestUtils.startJob(jobParameters);

		// then
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		Assertions.assertTrue(Files.exists(Paths.get("staging", "billing-2026-01.csv")));
	}

}