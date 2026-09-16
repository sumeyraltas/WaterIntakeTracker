package com.example.waterintake.domain.usecase;

import com.example.waterintake.domain.repository.WaterRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class UndoWaterIntakeUseCase_Factory implements Factory<UndoWaterIntakeUseCase> {
  private final Provider<WaterRepository> repositoryProvider;

  public UndoWaterIntakeUseCase_Factory(Provider<WaterRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UndoWaterIntakeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UndoWaterIntakeUseCase_Factory create(
      javax.inject.Provider<WaterRepository> repositoryProvider) {
    return new UndoWaterIntakeUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static UndoWaterIntakeUseCase_Factory create(
      Provider<WaterRepository> repositoryProvider) {
    return new UndoWaterIntakeUseCase_Factory(repositoryProvider);
  }

  public static UndoWaterIntakeUseCase newInstance(WaterRepository repository) {
    return new UndoWaterIntakeUseCase(repository);
  }
}
