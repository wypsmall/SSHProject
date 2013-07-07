package com.imti.framework.component.sertificate.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Shell√¸¡Ó÷¥––π‹¿Ì∆˜
 * @author zzg 2009-09-08
 *
 */
public class ShellMgr {
	private static final Log log=LogFactory.getLog(ShellMgr.class);
	public static void exec( String perlCmd){
		 try {
	            String osName = System.getProperty("os.name" );
	            String[] cmd = new String[3];
	                cmd[0] = "cmd.exe" ;
	                cmd[1] = "/C" ;
	                cmd[2] = perlCmd;
	            
	            Runtime rt = Runtime.getRuntime();
	            log.info("÷¥––√¸¡Ó: " + cmd[0] + " " + cmd[1] 
	                               + " " + cmd[2]);
	            Process proc = rt.exec(cmd);
	       	
			 // any error message?
	            StreamGobbler errorGobbler = new 
	                StreamGobbler( proc.getErrorStream(), "[√¸¡Ó––:]ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler = new 
	                StreamGobbler(proc.getInputStream(), "[√¸¡Ó––:]OUTPUT");
	                
	            //errorGobbler.setDaemon(true);
	            //outputGobbler.setDaemon(true);
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	          
	            // any error???
	            int exitVal = proc.waitFor();
	            log.info("[ShellMgr]ExitValue: " + exitVal);
	            
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
