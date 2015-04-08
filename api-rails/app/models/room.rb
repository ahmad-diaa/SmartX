class Room < ActiveRecord::Base
	belongs_to :user
	has_many :devices,dependent: :destroy
	validates :name,  presence: true, length: { maximum: 25 }, uniqueness: { case_sensitive: false }
end
