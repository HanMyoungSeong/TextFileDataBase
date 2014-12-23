package kr.ac.jejuuniv.library.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

public class FileTemplate<T> {

	private File file = null;
	private int horizontalLineCount = 0;

	public FileTemplate(String path) {
		file = new File(path);
		try {
			if (!file.isFile()) {
				new FileWriter(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateForObject(String data) {
		BufferedWriter bufferedWriter = null;
		try {
			generatedHorizontalLine();
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
			bufferedWriter.write(this.horizontalLineCount + "/" + data);
			bufferedWriter.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateForObject(ConvertableLineCallBack convertableLineCallBack) {
		File tempFile = null;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		try {
			tempFile = new File(file.getAbsolutePath() + ".tmp");

			bufferedReader = new BufferedReader(new FileReader(file));
			printWriter = new PrintWriter(new FileWriter(tempFile));

			try {
				convertableLineCallBack.execute(bufferedReader, printWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			printWriter.close();
			file.delete();
			tempFile.renameTo(file);

		}
	}

	public void updateForObject(ReadSentenceCallBack readLineSentenceCallBack) {
		Reader reader = null;
		BufferedReader bufferedReader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			bufferedReader = new BufferedReader(reader);
			readLineSentenceCallBack.execute(bufferedReader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public T updateForObject(ObjectReturnCallBack<T> objectReturnCallBack) {
		Reader reader = null;
		BufferedReader bufferedReader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			bufferedReader = new BufferedReader(reader);
			return objectReturnCallBack.execute(bufferedReader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void generatedHorizontalLine() {
		this.horizontalLineCount = 0;
		updateForObject(new ReadSentenceCallBack() {

			@Override
			public void execute(BufferedReader bufferedReader) throws Exception {
				String line = null;
				String number = null;
				while ((line = bufferedReader.readLine()) != null) {
					number = line.split("/")[0];
				}
				if (number != null)
					horizontalLineCount = Integer.parseInt(number) + 1;
			}
		});
	}
}