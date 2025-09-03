package io.github.ultimark.sd.teabreak.ezweb.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import io.github.ultimark.sd.teabreak.ezweb.exception.WebResourceDeploymentException;

public class StaticResourcesDeployerTask extends DefaultTask {

	/** 処理対象となるファイルの拡張子 */
	private static final String[] AVAILABLE_EXTENTIONS = new String[] { ".css", ".js", ".jpg", ".png", ".ico", ".gif" };
	
	/** モックアップ格納先ディレクトリ */
	private String sourceDirPath;
	
	/** モックアップ展開先ディレクトリ */
	private String resourceDestDirPath;
	
	/** タスク名 */
	public static final String TASK_NAME = "deployStaticResources";
	
	@TaskAction
	public void deployStaticResources() {
		
		/* モックアップ格納先ディレクトリ取得 */
		final File sourceDir = new File(getProject().getProjectDir(), sourceDirPath);
		// ディレクトリが存在しない場合は処理終了
		if (!sourceDir.exists()) {
			return;
		}
		
		/* ファイル処理 */
		processFile(sourceDir, sourceDir);
	}
	
	public void sourceDir(String path) {
		
		this.sourceDirPath = path;
	}
	
	public void resourceDestDir(String path) {
		
		this.resourceDestDirPath = path;
	}
	
	protected void processFile(File sourceDir, File root) {
		
		for (File file : root.listFiles()) {
			
			// HTML以外は無視
			if (!isTarget(file)) {
				
				continue;
			}
			
			// ディレクトリの場合は次のファイルへ
			if (file.isDirectory()) {
				
				processFile(sourceDir, file);
				continue;
			}
			
			// 展開先ディレクトリ取得
			final File destinationDir = new File(getProject().getProjectDir(), resourceDestDirPath);
			
			// 展開先Fileオブジェクト生成
			final File destinationFile = new File(
					destinationDir, 
					file.getAbsolutePath().replace(sourceDir.getAbsolutePath(), ""));
			
			// 親ディレクトリが存在しない場合は親ディレクトリを含むディレクトリ階層を作成
			if (!destinationFile.getParentFile().exists()) {
				
				if (!destinationFile.getParentFile().mkdirs()) {
					
					throw new WebResourceDeploymentException(
							"creating directory %s failed".formatted(destinationFile.getParentFile().getAbsolutePath()),
							new NoSuchFileException(destinationFile.getParentFile().getAbsolutePath()));
				}
			}
			
			try {
				
				// Spring Boot管理下に展開
				Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				
				throw new WebResourceDeploymentException(
						"Mockup resource deployment failed. source file : %s destination file %s"
						.formatted(file.getAbsolutePath(), destinationFile.getAbsolutePath())
						, e);
			}
		}
	}
	
	protected boolean isTarget(File file) {
		
		boolean isTarget = false;
		
		for (String extention : AVAILABLE_EXTENTIONS) {
			
			isTarget = file.getName().endsWith(extention);
			if (isTarget) break;
		}
		
		return isTarget;
	}
}
