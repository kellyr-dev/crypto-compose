package com.example.cryptocompose.domain.use_case.get_coins

import com.example.cryptocompose.data.repository.CoinRepository
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository : CoinRepository
) {



}