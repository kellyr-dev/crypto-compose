package com.example.cryptocompose.domain.use_case.get_coin

import com.example.cryptocompose.data.repository.CoinRepository
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository : CoinRepository
) {
}