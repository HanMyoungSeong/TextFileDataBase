package kr.ac.jejuuniv;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kr.ac.jejuuniv.template.ConvertableLineCallBack;
import kr.ac.jejuuniv.template.FileTemplate;
import kr.ac.jejuuniv.template.ObjectReturnCallBack;
import kr.ac.jejuuniv.template.ReadSentenceCallBack;

public class UserRepository {

	private FileTemplate<User> fileTemplate;

	public UserRepository() {
		fileTemplate = new FileTemplate<User>("/Users/hms/Documents/Workspace_Eclipse/TextFileDataBase/resources/kr/ac/jejuuniv/db/users.txt");
	}

	public void insert(User user) {
		this.fileTemplate.updateForObject(user.getName() + " " + user.getPassword());
	}

	public User select(final int id) {
		return fileTemplate.updateForObject(new ObjectReturnCallBack<User>() {

			@Override
			public User execute(BufferedReader bufferedReader) throws Exception {
				while (true) {
					String[] userInfo = bufferedReader.readLine().split(" ");
					if (userInfo[0].equals(id + "")) {
						int userId = Integer.parseInt(userInfo[0]);
						return new User(userId, userInfo[1], userInfo[2]);
					}
				}
			}
		});
	}

	public void delete(final int id) {
		fileTemplate.updateForObject(new ReadSentenceCallBack() {

			@Override
			public void execute(BufferedReader bufferedReader) throws Exception {
				while (true) {
					String[] userInfo = bufferedReader.readLine().split(" ");
					if (userInfo[0].equals(id + "")) {
						final String deletedLine = userInfo[0] + " " + userInfo[1] + " " + userInfo[2];
						fileTemplate.updateForObject(new ConvertableLineCallBack() {

							@Override
							public void execute(BufferedReader br, PrintWriter pw) throws Exception {
								String line = null;
								while ((line = br.readLine()) != null) {
									if (!line.trim().equals(deletedLine)) {
										pw.println(line);
										pw.flush();
									}
								}
							}
						});
						return;
					}
				}

			}
		});
	}

	public void update(final User user) {
		fileTemplate.updateForObject(new ReadSentenceCallBack() {

			@Override
			public void execute(BufferedReader bufferedReader) throws Exception {
				while (true) {
					final String[] userInfo = bufferedReader.readLine().split(" ");
					if (userInfo[0].equals(user.getId() + "")) {
						fileTemplate.updateForObject(new ConvertableLineCallBack() {

							@Override
							public void execute(BufferedReader br, PrintWriter pw) throws Exception {
								String line = null;
								while ((line = br.readLine()) != null) {
									if (line.trim().equals(userInfo[0] + " " + userInfo[1] + " " + userInfo[2])) {
										pw.println(user.getId() + " " + user.getName() + " " + user.getPassword());
									} else {
										pw.println(line);
									}
									pw.flush();
								}
							}
						});
						return;
					}
				}
			}
		});
	}

	public List<User> findAll() {
		final List<User> users = new ArrayList<User>();
		fileTemplate.updateForObject(new ReadSentenceCallBack() {

			@Override
			public void execute(BufferedReader bufferedReader) throws Exception {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					String[] userInfo = line.split(" ");
					User user = new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2]);
					users.add(user);
				}
			}
		});
		return users;
	}
}