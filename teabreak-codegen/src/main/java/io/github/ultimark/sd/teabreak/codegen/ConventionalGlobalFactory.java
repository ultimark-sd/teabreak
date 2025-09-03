package io.github.ultimark.sd.teabreak.codegen;

import org.seasar.doma.gradle.codegen.GlobalFactory;
import org.seasar.doma.gradle.codegen.desc.DaoDescFactory;
import org.seasar.doma.gradle.codegen.desc.EntityDescFactory;
import org.seasar.doma.gradle.codegen.desc.EntityPropertyDescFactory;
import org.seasar.doma.gradle.codegen.desc.NamingType;

import io.github.ultimark.sd.teabreak.codegen.dao.ConventionalDaoDescFactory;
import io.github.ultimark.sd.teabreak.codegen.entity.ConventionalEntityDescFactory;

public class ConventionalGlobalFactory extends GlobalFactory {

	@Override
	public EntityDescFactory createEntityDescFactory(
			String packageName, 
			Class<?> superclass,
			EntityPropertyDescFactory entityPropertyDescFactory, 
			NamingType namingType,
			String originalStatesPropertyName, 
			boolean showCatalogName, 
			boolean showSchemaName, 
			boolean showTableName,
			boolean showDbComment, 
			boolean useAccessor, 
			boolean useListener, 
			boolean useMetamodel,
			boolean useMappedSuperclass) {
		
		return new ConventionalEntityDescFactory(
				packageName, 
				superclass, 
				entityPropertyDescFactory, 
				namingType, 
				originalStatesPropertyName, 
				showCatalogName, 
				showSchemaName, 
				showTableName, 
				showDbComment, 
				useAccessor, 
				useListener, 
				useMetamodel, 
				useMappedSuperclass);
	}
	
	@Override
	public DaoDescFactory createDaoDescFactory(String packageName, String suffix, String configClassName) {

		return new ConventionalDaoDescFactory(packageName, suffix, configClassName);
	}
}