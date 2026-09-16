package com.example.waterintake.di;

import android.content.Context;
import com.example.waterintake.data.local.room.WaterDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideWaterDatabaseFactory implements Factory<WaterDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideWaterDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WaterDatabase get() {
    return provideWaterDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideWaterDatabaseFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideWaterDatabaseFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static DatabaseModule_ProvideWaterDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideWaterDatabaseFactory(contextProvider);
  }

  public static WaterDatabase provideWaterDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWaterDatabase(context));
  }
}
