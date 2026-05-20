# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/8 (100.0%)
- **Function parity:** 43/78 matched (target 61) — 55.1%
- **Class/type parity:** 6/6 matched (target 11) — 100.0%
- **Combined symbol parity:** 49/84 matched (target 72) — 58.3%
- **Average inline-code cosine:** 0.42 (function body across 7 matched files)
- **Average documentation cosine:** 0.16 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. rwlock

- **Target:** `serialtest.Rwlock`
- **Similarity:** 0.42
- **Dependents:** 1
- **Priority Score:** 1041205.8
- **Functions:** 4/8 matched (target 12)
- **Missing functions:** `drop`, `new`, `is_locked`, `parallel_count`
- **Types:** 4/4 matched (target 6)
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 2. file_lock

- **Target:** `serialtest.FileLock`
- **Similarity:** 0.29
- **Dependents:** 0
- **Priority Score:** 112207.1
- **Functions:** 10/21 matched (target 11)
- **Missing functions:** `gen_count_file`, `read_parallel_count`, `create_lockfile`, `unlock`, `write_parallel`, `init`, `assert_serially_locked_without_name`, `assert_serially_locked_with_multiple_names`, `assert_serially_locked_when_actually_locked_parallel`, `assert_serially_locked_outside_serial_lock`, `assert_serially_locked_in_different_thread`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/6 matched

### 3. code_lock

- **Target:** `serialtest.CodeLock`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 71604.5
- **Functions:** 8/15 matched (target 10)
- **Missing functions:** `parallel_count`, `is_locked`, `assert_serially_locked_without_name`, `assert_serially_locked_with_multiple_names`, `assert_serially_locked_when_actually_locked_parallel`, `assert_serially_locked_outside_serial_lock`, `assert_serially_locked_in_different_thread`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 4. parallel_file_lock

- **Target:** `serialtest.ParallelFileLock`
- **Similarity:** 0.21
- **Dependents:** 0
- **Priority Score:** 71107.9
- **Functions:** 4/11 matched (target 4)
- **Missing functions:** `unlock_ok`, `unlock_on_assert_sync_without_return`, `unlock_on_assert_sync_with_return`, `unlock_on_assert_async_without_return`, `demo_assert`, `call_serial_test_fn`, `unlock_on_assert_async_with_return`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 5. parallel_code_lock

- **Target:** `serialtest.ParallelCodeLock`
- **Similarity:** 0.59
- **Dependents:** 0
- **Priority Score:** 21104.1
- **Functions:** 9/11 matched (target 15)
- **Missing functions:** `demo_assert`, `call_serial_test_fn`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 4/6 matched

### 6. serial_code_lock

- **Target:** `serialtest.SerialCodeLock`
- **Similarity:** 0.40
- **Dependents:** 0
- **Priority Score:** 20606.0
- **Functions:** 4/6 matched (target 5)
- **Missing functions:** `test_hammer_check_new_key`, `unlock_on_assert`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 7. serial_file_lock

- **Target:** `serialtest.SerialFileLock`
- **Similarity:** 0.47
- **Dependents:** 0
- **Priority Score:** 20605.3
- **Functions:** 4/6 matched (target 4)
- **Missing functions:** `test_serial`, `unlock_on_assert_sync_without_return`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 8. lib

- **Target:** `serialtest.Lib [STUB]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/serial_test/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/serialtest kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
