package io.github.ultimark.sd.teabreak.codegen.entity;

import org.seasar.doma.gradle.codegen.desc.EntityDesc;
import org.seasar.doma.gradle.codegen.desc.EntityDescFactory;
import org.seasar.doma.gradle.codegen.desc.EntityPropertyDescFactory;
import org.seasar.doma.gradle.codegen.desc.NamingType;
import org.seasar.doma.gradle.codegen.meta.TableMeta;

public class ConventionalEntityDescFactory extends EntityDescFactory {

	public ConventionalEntityDescFactory(
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
		
		super(
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
	public EntityDesc createEntityDesc(TableMeta tableMeta) {
		
		return resolveEntityName(super.createEntityDesc(tableMeta), tableMeta);
	}
	
	@Override
	public EntityDesc createEntityDesc(TableMeta tableMeta, String entityPrefix, String entitySuffix) {

		return resolveEntityName(super.createEntityDesc(tableMeta, entityPrefix, entitySuffix), tableMeta);
	}
	
	@Override
	public EntityDesc createEntityDesc(
			TableMeta tableMeta, 
			String entityPrefix, String entitySuffix,
			String simpleName) {
		
		return resolveEntityName(super.createEntityDesc(tableMeta, entityPrefix, entitySuffix, simpleName), tableMeta);
	}
	
	private EntityDesc resolveEntityName(EntityDesc entityDesc, TableMeta tableMeta) {
		
		final String tableName = tableMeta.getName();
		final StringBuilder resolvedEntityName = new StringBuilder();
		
		for (String token : tableName.split("_")) {
			
			resolvedEntityName
				.append(Character.toString(token.charAt(0)).toUpperCase())
				.append(token.substring(1));
		}
		
		entityDesc.setSimpleName(resolvedEntityName.toString().substring(1));
		
		return entityDesc;
	}
}
