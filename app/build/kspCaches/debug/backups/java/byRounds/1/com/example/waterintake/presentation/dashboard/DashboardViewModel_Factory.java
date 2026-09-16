package com.example.waterintake.presentation.dashboard;

import com.example.waterintake.domain.repository.WaterRepository;
import com.example.waterintake.domain.usecase.AddWaterIntakeUseCase;
import com.example.waterintake.domain.usecase.UndoWaterIntakeUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<WaterRepository> repositoryProvider;

  private final Provider<AddWaterIntakeUseCase> addWaterIntakeUseCaseProvider;

  private final Provider<UndoWaterIntakeUseCase> undoWaterIntakeUseCaseProvider;

  public DashboardViewModel_Factory(Provider<WaterRepository> repositoryProvider,
      Provider<AddWaterIntakeUseCase> addWaterIntakeUseCaseProvider,
      Provider<UndoWaterIntakeUseCase> undoWaterIntakeUseCaseProvider) {
    this.repositoryProvider = repositoryProvider;
    this.addWaterIntakeUseCaseProvider = addWaterIntakeUseCaseProvider;
    this.undoWaterIntakeUseCaseProvider = undoWaterIntakeUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(repositoryProvider.get(), addWaterIntakeUseCaseProvider.get(), undoWaterIntakeUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      javax.inject.Provider<WaterRepository> repositoryProvider,
      javax.inject.Provider<AddWaterIntakeUseCase> addWaterIntakeUseCaseProvider,
      javax.inject.Provider<UndoWaterIntakeUseCase> undoWaterIntakeUseCaseProvider) {
    return new DashboardViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(addWaterIntakeUseCaseProvider), Providers.asDaggerProvider(undoWaterIntakeUseCaseProvider));
  }

  public static DashboardViewModel_Factory create(Provider<WaterRepository> repositoryProvider,
      Provider<AddWaterIntakeUseCase> addWaterIntakeUseCaseProvider,
      Provider<UndoWaterIntakeUseCase> undoWaterIntakeUseCaseProvider) {
    return new DashboardViewModel_Factory(repositoryProvider, addWaterIntakeUseCaseProvider, undoWaterIntakeUseCaseProvider);
  }

  public static DashboardViewModel newInstance(WaterRepository repository,
      AddWaterIntakeUseCase addWaterIntakeUseCase, UndoWaterIntakeUseCase undoWaterIntakeUseCase) {
    return new DashboardViewModel(repository, addWaterIntakeUseCase, undoWaterIntakeUseCase);
  }
}
