package com.openkm.gadmilagro.util;

import com.openkm.gadmilagro.config.Config;
//import com.openkm.invoice.config.GitPropertiesConfig;
import com.openkm.gadmilagro.service.CommonService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Component
public class UsageReport {
	private static final Logger log = LoggerFactory.getLogger(UsageReport.class);

	//@Autowired
	//private GitPropertiesConfig gitProps;

	@Autowired
	private CommonService cs;

	@Autowired
	private Config cfg;

	@Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 24 * 60 * 60 * 1000)
	public void doTask() {
		log.info("Task: {}", Thread.currentThread());
		String dialect = cfg.HIBERNATE_DIALECT;
		String version ="0";//gitProps.getBuildVersion();
		String hostName, okmUuid;

		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = "unknown";
		}

		try {
			okmUuid = cs.getAdminWebServices().repository.getRepositoryUuid();
		} catch (Exception e) {
			okmUuid = e.getClass().getSimpleName();
		}

		remoteCheck(okmUuid, "invoice-capture", version, hostName, dialect);
	}

	private void remoteCheck(String okmUuid, String modName, String modVersion, String serverName, String hbmDialect) {
		log.debug("remoteCheck({}, {})", okmUuid, modName);

		try {
			URL url = new URL("https://update.openkm.com/module");
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

			// @formatter:off
			String content = "okm_uuid=" + URLEncoder.encode(okmUuid, "UTF-8") +
					"&mod_name=" + URLEncoder.encode(modName, "UTF-8") +
					"&server_name=" + URLEncoder.encode(serverName, "UTF-8") +
					"&mod_version=" + URLEncoder.encode(modVersion, "UTF-8") +
					"&hbm_dialect=" + URLEncoder.encode(hbmDialect, "UTF-8");
			// @formatter:on
			log.debug("Request: {}", content);

			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Content-Length", String.valueOf(content.length()));

			// Send POST output.
			try (DataOutputStream printOut = new DataOutputStream(urlConn.getOutputStream())) {
				printOut.writeBytes(content);
				printOut.flush();
			}

			// Get response data.
			try (InputStream is = urlConn.getInputStream()) {
				String hash = IOUtils.toString(is, StandardCharsets.UTF_8);
				log.debug("Response: {}", hash);
				//verifyLicense(okmUuid, hash, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			//handleLicenseException();
		}

		log.debug("remoteCheck: void");
	}
}
