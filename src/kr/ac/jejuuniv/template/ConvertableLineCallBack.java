package kr.ac.jejuuniv.library.template;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ConvertableLineCallBack {

	void execute(BufferedReader bufferedReader, PrintWriter printWriter) throws Exception;

}
