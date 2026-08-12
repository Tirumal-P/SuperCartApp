# Fix Unresolved Reference Errors by Adding Missing Kotlin Plugin

The project currently has numerous "unresolved reference" errors because the Kotlin Android plugin is not applied in the Gradle configuration. This prevents the compiler and IDE from linking classes between different files and correctly handling Android-specific Kotlin extensions (like `viewModelScope` and ViewBinding).

## User Review Required

> [!IMPORTANT]
> This change adds the `org.jetbrains.kotlin.android` plugin (version 2.4.10) to your project. This is necessary for Kotlin support in an Android project.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/AndroidProject/SuperCartApp/gradle/libs.versions.toml)
- Add Kotlin version `2.4.10`.
- Add `kotlin-android` plugin definition.

#### [MODIFY] [build.gradle.kts](file:///C:/AndroidProject/SuperCartApp/build.gradle.kts)
- Apply the `kotlin-android` plugin at the top level (without applying it to the root project).

#### [MODIFY] [build.gradle.kts](file:///C:/AndroidProject/SuperCartApp/app/build.gradle.kts)
- Apply the `kotlin-android` plugin to the `:app` module.

## Verification Plan

### Automated Tests
- Run `gradle_sync` to ensure the configuration is valid.
- Run `analyze_file` on the affected files to confirm that the unresolved reference errors are resolved.

### Manual Verification
- The user can verify that the red squiggly lines in the IDE for classes like `HomeViewModel`, `CategoryItem`, and `ActivityMainBinding` have disappeared.
