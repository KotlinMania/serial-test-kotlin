# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/8 (87.5%)
- **Function parity:** 67/78 matched (target 90) — 85.9%
- **Class/type parity:** 6/6 matched (target 15) — 100.0%
- **Combined symbol parity:** 73/84 matched (target 105) — 86.9%
- **Average inline-code cosine:** 0.59 (function body across 7 matched files)
- **Average documentation cosine:** 0.14 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 4 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. rwlock

- **Target:** `serialtest.Rwlock`
- **Similarity:** 0.57
- **Dependents:** 1
- **Priority Score:** 1021204.3
- **Functions:** 6/8 matched (target 14)
- **Missing functions:** `is_locked`, `parallel_count`
- **Types:** 4/4 matched (target 6)
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 2. code_lock

- **Target:** `serialtest.CodeLock`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 31602.7
- **Functions:** 12/15 matched
- **Missing functions:** `parallel_count`, `is_locked`, `assert_serially_locked_in_different_thread`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 4/7 matched

### 3. parallel_file_lock

- **Target:** `serialtest.ParallelFileLock`
- **Similarity:** 0.57
- **Dependents:** 0
- **Priority Score:** 31104.3
- **Functions:** 8/11 matched (target 13)
- **Missing functions:** `unlock_ok`, `demo_assert`, `call_serial_test_fn`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 4/7 matched

### 4. parallel_code_lock

- **Target:** `serialtest.ParallelCodeLock`
- **Similarity:** 0.48
- **Dependents:** 0
- **Priority Score:** 21105.2
- **Functions:** 9/11 matched (target 13)
- **Missing functions:** `demo_assert`, `call_serial_test_fn`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 4/6 matched

### 5. file_lock

- **Target:** `serialtest.FileLock`
- **Similarity:** 0.62
- **Dependents:** 0
- **Priority Score:** 12203.8
- **Functions:** 20/21 matched (target 22)
- **Missing functions:** `init`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Tests:** 5/6 matched

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

- **Target:** `serialtest.SerialFileLock`
- **Similarity:** 0.67
- **Dependents:** 0
- **Priority Score:** 603.3
- **Functions:** 6/6 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 2/2 matched

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

