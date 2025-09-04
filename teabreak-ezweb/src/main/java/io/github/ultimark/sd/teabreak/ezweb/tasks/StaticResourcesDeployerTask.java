package io.github.ultimark.sd.teabreak.ezweb.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

import io.github.ultimark.sd.teabreak.ezweb.EzWebPlugin;
import io.github.ultimark.sd.teabreak.ezweb.exception.WebResourceDeploymentException;

public class StaticResourcesDeployerTask extends DefaultTask {

	/** 処理対象となるファイルの拡張子 */
	private static final String[] AVAILABLE_EXTENTIONS = new String[] { ".css", ".js", ".jpg", ".png", ".ico", ".gif" };
	
	/** モックアップ格納先ディレクトリ */
	private String sourceDirPath = EzWebPlugin.MOCKUP_SOURCE_DIR;
	
	/** モックアップ展開先ディレクトリ */
	private String resourceDestDirPath = EzWebPlugin.STATICS_DESTINATION_DIR;
	
	/** タスク名 */
	public static final String TASK_NAME = "deployStaticResources";
	
	/** ロガー */
	private Logger logger = getLogger();
	
	@TaskAction
	public void deployStaticResources() {
		
		logger.info("static resources deployment task start.");
		
		/* モックアップ格納先ディレクトリ取得 */
		final File sourceDir = new File(getProject().getProjectDir(), sourceDirPath);
		logger.info("mockup source directory : %s".formatted(sourceDir.getAbsolutePath()));
		// ディレクトリが存在しない場合は処理終了
		if (!sourceDir.exists()) {
			
			logger.info("mockup source directory does not exist. static resouces deployment task end normally.");
			return;
		}
		
		/* ファイル処理 */
		processFile(sourceDir, sourceDir);
		
		logger.info("static resources deployment task end normally.");
	}
	
	public void sourceDir(String path) {
		
		this.sourceDirPath = path;
	}
	
	public void resourceDestDir(String path) {
		
		this.resourceDestDirPath = path;
	}
	
	protected void processFile(File sourceDir, File root) {
		
		for (File file : root.listFiles()) {
			
			logger.info("Processing %s".formatted(file.getAbsolutePath()));

			// ディレクトリの場合は次のファイルへ
			if (file.isDirectory()) {
				
				processFile(sourceDir, file);
				continue;
			}
			
			// 対象ファイル以外は無視
			if (!isTarget(file)) {
				
				logger.info("%s is excluded from processing.".formatted(file.getAbsolutePath()));
				continue;
			}
			
			// 展開先ディレクトリ取得
			final File destinationDir = new File(getProject().getProjectDir(), resourceDestDirPath);
			logger.info("mockup deployment directory : %s".formatted(destinationDir.getAbsolutePath()));
			
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
