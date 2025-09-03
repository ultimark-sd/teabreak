package io.github.ultimark.sd.teabreak.codegen;

import org.gradle.api.Project;
import org.seasar.doma.gradle.codegen.CodeGenPlugin;

/**
 * JPA(Java Persistence API)に準拠したEntity、DAOをDBスキーマから自動的に生成するタスクを提供するGradle Pluginです。<br>
 * このGradle PluginはDoma CodeGenをラップしています。
 */
public class CodeGeneratorPlugin extends CodeGenPlugin {
	
	
	@Override
	public void apply(Project project) {
		// TODO 自動生成されたメソッド・スタブ
		super.apply(project);
	}
}