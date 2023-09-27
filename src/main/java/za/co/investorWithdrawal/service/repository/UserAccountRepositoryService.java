package za.co.investorWithdrawal.service.repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import za.co.investorWithdrawal.data.ProductInfo;
import za.co.investorWithdrawal.constants.ProductType;
import za.co.investorWithdrawal.service.Utils;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountRepositoryService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<ProductInfo> getUserProduct(Long userId) {
        Optional<UserInfoEntity> possibleUser = userInfoRepository.findById(userId);
        if (possibleUser.isPresent()) {
            List<UserAccountEntity> accountList = userAccountRepository
                    .findAllByUserId(possibleUser.get().getId());

            return makeProductInfoList(accountList);
        } else {
            return null;
        }
    }

    // -----------------------------------UTILS------------------------------------

    private ProductInfo makeProductInfo(UserAccountEntity account) {
        return ProductInfo.builder()
                .prodId(account.getAccountNumber())
                .type(ProductType.of(account.getProductType().getType()))
                .name(account.getProductType().getDescription())
                .balance(Utils.fromBigDecimalToRands(account.getBalance()))
                .build();
    }

    private List<ProductInfo> makeProductInfoList(List<UserAccountEntity> accountList) {
        List<ProductInfo> productInfoList = new LinkedList<>();
        for (UserAccountEntity account : accountList) {
            ProductInfo product = makeProductInfo(account);
            productInfoList.add(product);
        }
        return productInfoList;
    }

}
