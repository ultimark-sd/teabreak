package io.github.ultimark.sd.teabreak.ezweb;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

import io.github.ultimark.sd.teabreak.ezweb.tasks.HtmlDeployerTask;
import io.github.ultimark.sd.teabreak.ezweb.tasks.StaticResourcesDeployerTask;

public class EzWebPlugin implements Plugin<Project> {

	/** モックアップの格納先ディレクトリ */
	public static final String MOCKUP_SOURCE_DIR = "src/main/resources/mockup";
	/** HTMLの展開先ディレクトリ */
	public static final String HTML_DESTINATION_DIR = "src/main/resources/templates";
	/** 静的リソース（CSS・JavaScript・画像ファイル 等）の展開先ディレクトリ */
	public static final String STATICS_DESTINATION_DIR = "src/main/resources/static";
	/** タスクグループ名 */
	public static final String TASK_GROUP_NAME = "Teabreak EzWeb";
	
	@Override
	public void apply(Project project) {
		
		/* HTML展開タスク登録 */
		final TaskProvider<HtmlDeployerTask> htmlDeployer = project
				.getTasks()
				.register(HtmlDeployerTask.TASK_NAME, HtmlDeployerTask.class);
		
		htmlDeployer.configure(config -> {
			config.setGroup(TASK_GROUP_NAME);
		});
		
		/* 静的リソース展開タスク登録 */
		final TaskProvider<StaticResourcesDeployerTask> staticResourceDeployer = project
				.getTasks()
				.register(StaticResourcesDeployerTask.TASK_NAME, StaticResourcesDeployerTask.class);
		
		staticResourceDeployer.configure(config -> {
			config.setGroup(TASK_GROUP_NAME);
		});
		
	}
}