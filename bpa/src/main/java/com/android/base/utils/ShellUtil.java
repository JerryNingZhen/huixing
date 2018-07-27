package com.android.base.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 * Android Shell工具类，用于检查系统root权限，并在shell或root用户下执行shell命令。
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ShellUtil {

	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_EXIT = "exit\n";
	public static final String COMMAND_LINE_END = "\n";

	/**
	 * 检查root权限
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午6:57:21
	 * <br> UpdateTime: 2016-1-23,下午6:57:21
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return true==root
	 */
	public static boolean checkRootPermission() {
		return execCommand(new String[] { "echo root" }, true, false).result == 0;
	}

	/**
	 * 执行shell命令方法
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-23,下午6:57:21
	 * <br> UpdateTime: 2016-1-23,下午6:57:21
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param commands
	 *            命令数组
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            是否需要返回值
	 * @return 如果isNeedResultMsg == false, CommandResult.successMsg==null , CommandResult.errorMsg==null, CommandResult,result== -1
	 */
	public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
		int result = -1;
		if (commands == null || commands.length == 0) {
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands) {
				if (command == null) {
					continue;
				}

				// donnot use os.writeBytes(commmand), avoid chinese charset
				// error
				os.write(command.getBytes());
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();

			result = process.waitFor();
			// get command result
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null) {
					errorMsg.append(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}
		return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null : errorMsg.toString());
	}

	/**
	 * 命令运行的结果
	 * 
	 * <br> Author: 叶青
	 * <br> Version: 1.0.0
	 * <br> Date: 2016年12月11日
	 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
	 */
	public static class CommandResult {

		/** 命令运行的结果 0表示正常,否则意味着错误 **/
		public int result;
		/** 命令运行结果的成功消息 **/
		public String successMsg;
		/** 命令运行结果的错误消息 **/
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}

	// /**
	// * 执行shell单个命令
	// *
	// * @param command
	// * 命令
	// * @param isRoot
	// * 是否root
	// * @return
	// */
	// public static CommandResult execCommand(String command, boolean isRoot) {
	// return execCommand(command, isRoot, true);
	// }
	//
	// /**
	// * 执行shell单个命令
	// *
	// * @param command
	// * 命令
	// * @param isRoot
	// * 是否root
	// * @param isNeedResultMsg
	// * 是否需要返回值
	// * @return
	// */
	// public static CommandResult execCommand(String command, boolean isRoot,
	// boolean isNeedResultMsg) {
	// return execCommand(new String[] { command }, isRoot, isNeedResultMsg);
	// }
	//
	// /**
	// * 执行shell多个命令
	// *
	// * @param commands
	// * 命令数组
	// * @param isRoot
	// * 是否root
	// * @return
	// */
	// public static CommandResult execCommand(String[] commands, boolean
	// isRoot) {
	// return execCommand(commands, isRoot, true);
	// }
	//
	// /**
	// * 执行shell多个命令
	// *
	// * @param commands
	// * 命令数组
	// * @param isRoot
	// * 是否root
	// * @return
	// */
	// public static CommandResult execCommand(List<String> commands, boolean
	// isRoot) {
	// return execCommand(commands == null ? null : commands.toArray(new
	// String[] {}), isRoot, true);
	// }
	//
	// /**
	// * 执行shell多个命令
	// *
	// * @param commands
	// * 命令数组
	// * @param isRoot
	// * 是否root
	// * @param isNeedResultMsg
	// * 是否需要返回值
	// * @return
	// */
	// public static CommandResult execCommand(List<String> commands, boolean
	// isRoot, boolean isNeedResultMsg) {
	// return execCommand(commands == null ? null : commands.toArray(new
	// String[] {}), isRoot, isNeedResultMsg);
	// }
}