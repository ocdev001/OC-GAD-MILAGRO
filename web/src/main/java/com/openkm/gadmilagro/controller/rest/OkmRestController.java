package com.openkm.gadmilagro.controller.rest;

import com.openkm.gadmilagro.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST API is used by OpenKM plugins to retrieve data because maybe invoice applications in several openkm tenant.
 * Each invoice application in each tenant must have its own data ( users, parameters etc. ), the REST API does it.
 */
@RestController
@RequestMapping("/rest")
public class OkmRestController {
	private static final Logger log = LoggerFactory.getLogger(OkmRestController.class);


	@Autowired
	private Config config;



	@GetMapping("/rootDestinationFolder")
	public String getRootDestinationFolder() {
		log.debug("getRootDestinationFolder()");
		String rootDestinationFolder = config.ROOT_DESTINATION_FOLDER;
		log.debug("getRootDestinationFolder: {}", rootDestinationFolder);
		return rootDestinationFolder;
	}

}
