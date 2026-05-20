# serial-test-kotlin symbol parity verification

Run time: 2026-05-19 20:27 PDT

## Build artifact proof

Command run:

```bash
./gradlew build --no-daemon --console=plain --no-configuration-cache
```

Result: `BUILD SUCCESSFUL in 3m 38s`, with 118 actionable tasks reported by Gradle.

Real artifacts produced and inspected:

- `build/libs/serial-test-kotlin-jvm-0.1.0.jar`
- `build/libs/serial-test-kotlin-metadata-0.1.0.jar`
- `build/libs/serial-test-kotlin-js-0.1.0.klib`
- `build/libs/serial-test-kotlin-wasm-js-0.1.0.klib`
- `build/libs/serial-test-kotlin-wasm-wasi-0.1.0.klib`
- `build/outputs/aar/serial-test-kotlin.aar`
- native metadata jars for Android Native, iOS, Linux, macOS, MinGW, tvOS, and watchOS
- debug and release `SerialTest.framework` outputs for iOS, macOS, tvOS, and watchOS targets

The JVM jar and Android AAR `classes.jar` both contain the expected generated class files for:

- `CodeLockKt`
- `FileLockKt`
- `ParallelCodeLockKt`
- `ParallelFileLockKt`
- `SerialCodeLockKt`
- `SerialFileLockKt`
- `Lock`
- `LockData`
- `LockState`
- `Locks`
- `MutexGuardWrapper`
- `UniqueReentrantMutex`

## Manual source symbol unpacking

Rust source files inspected:

- `tmp/serial_test/src/lib.rs`
- `tmp/serial_test/src/rwlock.rs`
- `tmp/serial_test/src/code_lock.rs`
- `tmp/serial_test/src/parallel_code_lock.rs`
- `tmp/serial_test/src/serial_code_lock.rs`
- `tmp/serial_test/src/file_lock.rs`
- `tmp/serial_test/src/parallel_file_lock.rs`
- `tmp/serial_test/src/serial_file_lock.rs`

Kotlin source files inspected:

- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/Lib.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/Rwlock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/CodeLock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/ParallelCodeLock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/SerialCodeLock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/FileLock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/ParallelFileLock.kt`
- `src/commonMain/kotlin/io/github/kotlinmania/serialtest/SerialFileLock.kt`

## Public runtime symbol mapping

| Rust symbol | Kotlin symbol | Status |
|---|---|---|
| `is_locked_serially` | `isLockedSerially` | Ported |
| `local_parallel_core` | `localParallelCore` | Ported |
| `local_parallel_core_with_return` | `localParallelCoreWithReturn` | Ported |
| `local_async_parallel_core` | `localAsyncParallelCore` | Ported |
| `local_async_parallel_core_with_return` | `localAsyncParallelCoreWithReturn` | Ported |
| `local_serial_core` | `localSerialCore` | Ported |
| `local_serial_core_with_return` | `localSerialCoreWithReturn` | Ported |
| `local_async_serial_core` | `localAsyncSerialCore` | Ported |
| `local_async_serial_core_with_return` | `localAsyncSerialCoreWithReturn` | Ported |
| `is_locked_file_serially` | `isLockedFileSerially` | Ported |
| `fs_parallel_core` | `fsParallelCore` | Ported |
| `fs_parallel_core_with_return` | `fsParallelCoreWithReturn` | Ported |
| `fs_async_parallel_core` | `fsAsyncParallelCore` | Ported |
| `fs_async_parallel_core_with_return` | `fsAsyncParallelCoreWithReturn` | Ported |
| `fs_serial_core` | `fsSerialCore` | Ported |
| `fs_serial_core_with_return` | `fsSerialCoreWithReturn` | Ported |
| `fs_async_serial_core` | `fsAsyncSerialCore` | Ported |
| `fs_async_serial_core_with_return` | `fsAsyncSerialCoreWithReturn` | Ported |
| `parallel` derive macro re-export | none | Not ported; no Kotlin macro equivalent yet |
| `serial` derive macro re-export | none | Not ported; no Kotlin macro equivalent yet |
| `file_parallel` derive macro re-export | none | Not ported; no Kotlin macro equivalent yet |
| `file_serial` derive macro re-export | none | Not ported; no Kotlin macro equivalent yet |

## Internal type mapping

| Rust type | Kotlin type | Status |
|---|---|---|
| `LockState` | `LockState` | Ported |
| `LockData` | `LockData` | Ported |
| `Locks` | `Locks` | Ported |
| `MutexGuardWrapper` | `MutexGuardWrapper` | Ported |
| `UniqueReentrantMutex` | `UniqueReentrantMutex` | Ported |
| `Lock` | `Lock` | Ported |

## Remaining symbol mismatches

`ast_distance --symbol-parity tmp/serial_test/src src/commonMain/kotlin/io/github/kotlinmania/serialtest --missing-only`
reported:

- Production definitions: 51/56 matched (91.1%).
- Production top-level functions: 24/24 matched (100.0%).
- Structs: 6/6 matched (100.0%).
- Impl methods: 21/26 matched (80.8%).
- Test definitions: 0/32 matched.

Remaining private production method gaps:

- `drop` from `rwlock.rs`
- `gen_count_file` from `file_lock.rs`
- `read_parallel_count` from `file_lock.rs`
- `create_lockfile` from `file_lock.rs`
- `write_parallel` from `file_lock.rs`

## Function comparison cross-check

The stricter file-by-file `--compare-functions` mode gives the same core
missing-function set, but not the exact same list as `--symbol-parity`.

Commands run:

```bash
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/rwlock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/Rwlock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/code_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/CodeLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/parallel_code_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/ParallelCodeLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/serial_code_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/SerialCodeLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/file_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/FileLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/parallel_file_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/ParallelFileLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/serial_file_lock.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/SerialFileLock.kt kotlin
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --compare-functions tmp/serial_test/src/lib.rs rust src/commonMain/kotlin/io/github/kotlinmania/serialtest/Lib.kt kotlin
```

Strict function comparison reported 39 unmatched Rust functions across those
pairs. That is the same 37 real missing items from the broader symbol pass
plus two stricter name-shape findings:

- `Locks::new` in `rwlock.rs` is reported missing because the Kotlin port uses
  a constructor, not a named `new` function.
- `Lock::unlock` in `file_lock.rs` is reported missing by file-by-file function
  comparison; the broader symbol parity pass does not list it in the 5 missing
  impl methods, so this needs explicit review before claiming method parity.

The remaining 37 unmatched functions are:

- `drop` from `rwlock.rs`
- `gen_count_file`, `read_parallel_count`, `create_lockfile`, and
  `write_parallel` from `file_lock.rs`
- the 32 upstream inline test functions listed by `--symbol-parity`

Remaining supplementary gaps:

- Rust `NAME1` / `NAME2` constants exist only inside upstream inline tests and are not ported.
- Upstream inline `#[test]` functions are not ported into Kotlin tests yet.

## Certification status

This port is not complete. The build artifacts are real and the public runtime symbol surface is present, but the symbol unpacking does not certify that there is no more to do.

The next work is:

1. Port the upstream inline tests into Kotlin test sources.
2. Decide whether the private file-lock helper methods need direct Kotlin counterparts or whether the current in-memory commonMain shape should be replaced with platform `expect` / `actual` file locking.
3. Re-run `ast_distance --deep` and `ast_distance --symbol-parity` after those changes.
