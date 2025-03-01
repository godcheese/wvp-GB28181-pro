package com.genersoft.iot.vmp.streamPush.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScriptUtil {

	/**
	 * @param pathOrCommand 脚本路径或者命令
	 * @return
	 */
	public static List<String> exceCommond(String pathOrCommand) {
		List<String> result = new ArrayList<>();

		try {
			// 执行脚本
			Process ps = Runtime.getRuntime().exec(pathOrCommand);
			int exitValue = ps.waitFor();
//			if (0 != exitValue) {
//				String errorMsg = "call shell failed. error code is :" + exitValue;
//				Throw.bizStatusException(errorMsg);
//			}

			// 只能接收脚本echo打印的数据，并且是echo打印的最后一次数据
			BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
			br.close();

			// 只能接收脚本echo打印的数据，并且是echo打印的最后一次数据
			BufferedInputStream error = new BufferedInputStream(ps.getErrorStream());
			BufferedReader brerror = new BufferedReader(new InputStreamReader(error));
			String lineError;
			while ((lineError = brerror.readLine()) != null) {
				log.error(lineError);
			}
			error.close();
			brerror.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}