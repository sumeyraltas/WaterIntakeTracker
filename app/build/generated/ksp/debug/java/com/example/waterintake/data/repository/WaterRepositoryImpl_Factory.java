package com.example.waterintake.data.repository;

import com.example.waterintake.data.datastore.UserPreferencesRepository;
import com.example.waterintake.data.local.room.WaterDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WaterRepositoryImpl_Factory implements Factory<WaterRepositoryImpl> {
  private final Provider<WaterDao> waterDaoProvider;

  private final Provider<UserPreferencesRepository> preferencesRepositoryProvider;

  public WaterRepositoryImpl_Factory(Provider<WaterDao> waterDaoProvider,
      Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    this.waterDaoProvider = waterDaoProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public WaterRepositoryImpl get() {
    return newInstance(waterDaoProvider.get(), preferencesRepositoryProvider.get());
  }

  public static WaterRepositoryImpl_Factory create(javax.inject.Provider<WaterDao> waterDaoProvider,
      javax.inject.Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    return new WaterRepositoryImpl_Factory(Providers.asDaggerProvider(waterDaoProvider), Providers.asDaggerProvider(preferencesRepositoryProvider));
  }

  public static WaterRepositoryImpl_Factory create(Provider<WaterDao> waterDaoProvider,
      Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    return new WaterRepositoryImpl_Factory(waterDaoProvider, preferencesRepositoryProvider);
  }

  public static WaterRepositoryImpl newInstance(WaterDao waterDao,
      UserPreferencesRepository preferencesRepository) {
    return new WaterRepositoryImpl(waterDao, preferencesRepository);
  }
}
