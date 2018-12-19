/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2018 Nuance Communications Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Nuance Communications Inc. The program(s) may be used and/or copied
 * only with the written permission from Nuance Communications Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * ---------------------------------------------------------------------------
 */
package com.nuance.test.grpc.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SpringBootApplication
public class App {

	private static final Log log = LogFactory.getLog(App.class);

	public static void main(String[] args) throws Exception {
		log.info("Starting App");
		GracefulshutdownSpringApplication.run(App.class, args);
	}
}
