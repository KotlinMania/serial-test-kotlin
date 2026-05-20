# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/8 (100.0%)
- **Function parity:** 61/78 matched (target 89) — 78.2%
- **Class/type parity:** 6/6 matched (target 16) — 100.0%
- **Combined symbol parity:** 67/84 matched (target 105) — 79.8%
- **Average inline-code cosine:** 0.58 (function body across 7 matched files)
- **Average documentation cosine:** 0.00 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. rwlock

- **Target:** `serialtest.Rwlock`
- **Similarity:** 0.52
- **Dependents:** 1
- **Priority Score:** 1021204.8
- **Functions:** 6/8 matched (target 14)
- **Missing functions:** `is_locked`, `parallel_count`
- **Types:** 4/4 matched (target 6)
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 2. file_lock

- **Target:** `serialtest.FileLock`
- **Similarity:** 0.39
- **Dependents:** 0
- **Priority Score:** 72206.1
- **Functions:** 14/21 matched (target 16)
- **Missing functions:** `gen_count_file`, `read_parallel_count`, `create_lockfile`, `unlock`, `write_parallel`, `init`, `assert_serially_locked_in_different_thread`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Tests:** 4/6 matched

### 3. code_lock

- **Target:** `serialtest.CodeLock`
- **Similarity:** 0.72
- **Dependents:** 0
- **Priority Score:** 31602.8
- **Functions:** 12/15 matched (target 16)
- **Missing functions:** `parallel_count`, `is_locked`, `assert_serially_locked_in_different_thread`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Tests:** 4/7 matched

### 4. parallel_file_lock

- **Target:** `serialtest.FsParallelCore`
- **Similarity:** 0.69
- **Dependents:** 0
- **Priority Score:** 31103.1
- **Functions:** 8/11 matched (target 15)
- **Missing functions:** `unlock_ok`, `demo_assert`, `call_serial_test_fn`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 4/7 matched

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
- **Similarity:** 0.48
- **Dependents:** 0
- **Priority Score:** 605.2
- **Functions:** 6/6 matched (target 7)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 7. serial_file_lock

- **Target:** `serialtest.FsSerialCore`
- **Similarity:** 0.67
- **Dependents:** 0
- **Priority Score:** 603.3
- **Functions:** 6/6 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 2/2 matched

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
