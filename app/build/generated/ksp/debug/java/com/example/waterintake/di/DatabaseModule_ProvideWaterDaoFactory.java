package com.example.waterintake.di;

import com.example.waterintake.data.local.room.WaterDao;
import com.example.waterintake.data.local.room.WaterDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class DatabaseModule_ProvideWaterDaoFactory implements Factory<WaterDao> {
  private final Provider<WaterDatabase> databaseProvider;

  public DatabaseModule_ProvideWaterDaoFactory(Provider<WaterDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WaterDao get() {
    return provideWaterDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWaterDaoFactory create(
      javax.inject.Provider<WaterDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWaterDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideWaterDaoFactory create(
      Provider<WaterDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWaterDaoFactory(databaseProvider);
  }

  public static WaterDao provideWaterDao(WaterDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWaterDao(database));
  }
}
